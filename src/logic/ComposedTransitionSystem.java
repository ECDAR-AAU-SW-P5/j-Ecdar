package logic;
import models.*;
import java.util.*;

public class ComposedTransitionSystem extends TransitionSystem {
		private ArrayList<Component> machines;
		private Set<Channel> inputs, outputs, inputsOutside, outputsOutside, syncs;

		public ComposedTransitionSystem(ArrayList<Component> machines) {
				super(machines);
				this.machines = machines;
				inputsOutside = new HashSet<>();
				outputsOutside = new HashSet<>();
				inputs = new HashSet<>();
				outputs = new HashSet<>();
				syncs = new HashSet<>();

				for (int i = 0; i < machines.size(); i++) {
						Set<Channel> inputsOfI, outputsOfI, sync, outputsOfOthers, inputsOfOthers;
						inputsOfI = new HashSet<>(machines.get(i).getInputAct());
						outputsOfI = new HashSet<>(machines.get(i).getOutputAct());
						sync = new HashSet<>(machines.get(i).getOutputAct());
						outputsOfOthers = new HashSet<>();
						inputsOfOthers  = new HashSet<>();
						inputs.addAll(inputsOfI);
						outputs.addAll(outputsOfI);

						for (int j = 0; j < machines.size(); j++) {
								if (i != j) {
										// check if output actions overlap
										Set<Channel> diff = new HashSet<>(machines.get(i).getOutputAct());
										diff.retainAll(machines.get(j).getOutputAct());
										if (!diff.isEmpty()) {
												throw new IllegalArgumentException("machines cannot be composed");
										}

										outputsOfOthers.addAll(machines.get(j).getOutputAct());

										inputsOfOthers.addAll(machines.get(j).getInputAct());

										Set<Channel> syncCopy = new HashSet<>(sync);
										syncCopy.retainAll(machines.get(j).getInputAct());
										syncs.addAll(syncCopy);
								}
						}

						// set difference
						inputsOfI.removeAll(outputsOfOthers);
						outputsOfI.removeAll(inputsOfOthers);

						inputsOutside.addAll(inputsOfI);
						outputsOutside.addAll(outputsOfI);
				}
				outputs.removeAll(syncs);
		}

		public Set<Channel> getInputs() {
				return inputs;
		}

		public Set<Channel> getOutputs() {
				return outputs;
		}

		public ArrayList<State> getNextStates(State state, Channel channel) {
				ArrayList<State> states = new ArrayList<>();
				ArrayList<Location> locations = state.getLocations();

				if (outputsOutside.contains(channel)) {
						for (int i = 0; i < locations.size(); i++) {
								ArrayList<Transition> transitions = machines.get(i).getTransitionsFromLocationAndSignal(locations.get(i), channel);

								for (Transition transition : transitions) {
										ArrayList<Location> newLocations = new ArrayList<>();
										for (int j = 0; j < locations.size(); j++) {
												if (i != j) {
														newLocations.add(locations.get(j));
												} else {
														newLocations.add(transition.getTo());
												}
										}
										int[] dbm = state.getZone();
										// apply guards
										if (!transition.getGuards().isEmpty())
												dbm = applyInvariantsOrGuards(dbm, transition.getGuards());
										// apply resets
										if (!transition.getUpdates().isEmpty()) dbm = applyResets(dbm, transition.getUpdates());
										// apply invariants
										if (!getInvariants(newLocations).isEmpty())
												dbm = applyInvariantsOrGuards(dbm, getInvariants(newLocations));

										// construct new state and add it to list
										State newState = new State(newLocations, dbm);
										states.add(newState);
								}
						}
				} else if (inputsOutside.contains(channel) || (syncs.contains(channel))) {
						ArrayList<ArrayList<Location>> newLocationsArr = new ArrayList<>();
						ArrayList<ArrayList<Transition>> transitionsArr = new ArrayList<>();

						for (int j = 0; j < locations.size(); j++) {
								ArrayList<Transition> transitionsForJ = machines.get(j).getTransitionsFromLocationAndSignal(locations.get(j), channel);
								if (j == 0) {
										// no inputs to locations.get(j), so we keep the same location
										if (transitionsForJ.isEmpty()) {
												newLocationsArr.add(new ArrayList<>(Arrays.asList(locations.get(j))));
												transitionsArr.add(new ArrayList<>());
										} else {
												for (Transition t : transitionsForJ) {
														newLocationsArr.add(new ArrayList<>(Arrays.asList(t.getTo())));
														transitionsArr.add(new ArrayList<>(Arrays.asList(t)));
												}
										}
								} else {
										if (transitionsForJ.isEmpty()) {
												for (ArrayList<Location> locationArr : newLocationsArr) {
														locationArr.add(locations.get(j));
												}
										} else {
												ArrayList<ArrayList<Location>> newLocationsArrCopy = new ArrayList<>();
												for (ArrayList<Location> locs : newLocationsArr) {
														ArrayList<Location> newLocs = new ArrayList<>(locs);
														newLocationsArrCopy.add(newLocs);
												}
												ArrayList<ArrayList<Transition>> transitionsArrCopy = new ArrayList<>(transitionsArr);
												for (ArrayList<Transition> transitions : transitionsArr) {
														ArrayList<Transition> newTransitions = new ArrayList<>(transitions);
														transitionsArrCopy.add(newTransitions);
												}

												for (int x = 0; x < transitionsForJ.size(); x++) {
														Transition t = transitionsForJ.get(x);
														for (int y = 0; y < newLocationsArrCopy.size(); y++) {
																if (x == 0) {
																		newLocationsArr.get(y).add(t.getTo());
																} else {
																		ArrayList<Location> newLocationArr = new ArrayList<>(newLocationsArrCopy.get(y));
																		ArrayList<Transition> newTransitionArr = new ArrayList<>(transitionsArrCopy.get(y));
																		newLocationArr.add(t.getTo());
																		newTransitionArr.add(t);
																		newLocationsArr.add(newLocationArr);
																		transitionsArr.add(newTransitionArr);
																}
														}
												}
										}
								}
						}

						for (int n = 0; n < newLocationsArr.size(); n++) {
								ArrayList<Location> newLocations = newLocationsArr.get(n);
								ArrayList<Guard> guards = new ArrayList<>();
								ArrayList<Update> updates = new ArrayList<>();
								for (Transition t : transitionsArr.get(n)) {
										guards.addAll(t.getGuards());
										updates.addAll(t.getUpdates());
								}

								int[] dbm = state.getZone();
								// apply guards
								if (!guards.isEmpty())
										dbm = applyInvariantsOrGuards(dbm, guards);
								// apply resets
								if (!updates.isEmpty()) dbm = applyResets(dbm, updates);
								// apply invariants
								if (!getInvariants(newLocations).isEmpty())
										dbm = applyInvariantsOrGuards(dbm, getInvariants(newLocations));

								// construct new state and add it to list
								State newState = new State(newLocations, dbm);
								states.add(newState);
						}
				}

				return states;
		}

		public State getInitialState() {
				ArrayList<Location> initialLocations = new ArrayList<>();

				for (Component machine : machines) {
						Location init = machine.getInitLoc();
						initialLocations.add(init);
				}

				int[] zone = initializeDBM();
				zone = applyInvariantsOrGuards(zone, getInvariants(initialLocations));

				return new State(initialLocations, zone);
		}

		private ArrayList<Guard> getInvariants(ArrayList<Location> locations) {
				ArrayList<Guard> invariants = new ArrayList<>();

				for (Location location : locations) {
						Guard invariant = location.getInvariant();
						if (invariant != null) invariants.add(invariant);
				}

				return invariants;
		}
}