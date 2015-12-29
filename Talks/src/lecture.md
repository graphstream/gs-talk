---
title: GraphStream <small>  A Dynamic Graph Library </small>
author:
  - Yoann Pigné
date: NetSciX2016 -- School of Code

center: 0
controls: 0
height: 800
hideAddressBar: true
history: true
keyboard: true
loop: true
margin: 0.1
#maxScale: 3.0
#minScale: 0.1
overview: true
#parallaxBackgroundHorizontal: 1
#parallaxBackgroundImage: none
#parallaxBackgroundSize: '4995px 135px'
progress: 0
slideNumber: 1
theme: none
touch: true
transition: linear
#transition: none/fade/slide/convex/concave/zoom
transitionSpeed: fast
width: 1280

---

#Outlook

- Dynamic Graphs
- GraphStream
- The Event Model
- Algorithms
- Visualisation
- Interaction with other tools
- Demos
    * basic demos
    * simulation of a mobile ad hoc network  


#First, static graphs
## Structure:{data-xx="ok"}

- Nodes, Vertices
- (undirected) Edges, Links
- (directed) Arcs


![Simple Network](/img/simple-network.svg)



##Algorithms: Graphs Theory

- graph colouring problems
- routing problems
- flow problems
- covering problems
- subgraphs problems



# When we add dynamics...

## What kind of dynamics?

- values/weight on edges or nodes?
- nodes and/or edges added/removed?

![Weighted Network](/img/weighted-network.svg)


## Problem with algorithms

- As soon as it gets computed, the result has vanished.
- Can we stop the graph and recompute?
- Depends on the dynamic graph model.


![Time-Varying Network](/img/varying-network.svg)


#Dynamic Graph Models


Many graph models consider dynamics in some ways. But they are usually bounded to their application domain.

- Is there a general-enough model that can be used in a broad range of applications?
- What about a **Dynamic Graph Theory** with algorithms for colouring, routing, flows, etc.?

# Complex Networks

1. **Exploration**: Analysis of "real world" networks (web graphs, biological networks, social networks)

2. **Modelling**: Build artificial networks (Barabasi-Albert, Watts-Strogatz, Dorogovtsev-Mendes, Golomb
, etc.)


- Measures on graphs: community, distribution, dimensions, etc.
- Iterative Construction/Iteration: we see dynamic graphs here!

# Aggregative Methods
All the evolution is known **in advance**, the dynamic graph is aggregated into a static graph. (Temporal Networks, Evolving Graphs, Time-Varying Graphs, etc.)

Why? Because it allows the use of classical graph theory.

![Aggregative Methods](/img/temporal-network.svg)



#Re-optimisation

Build and maintain structures on dynamic graphs (e.g. spanning trees) **without** prior knowledge of the evolution.

**Hypothesis**: Updating an existing structure after some modifications is more effective that recomputing it from scratch.


![Re-optimization](/img/re-optimization.svg)

#test {data-background="url('/img/leHavreStep2_.png')"}

----
<section data-background="url('/img/leHavreStep2_.png')" data-background-repeat="no-repeat" data-background-size="100%">
</section>

<!-- END -->
