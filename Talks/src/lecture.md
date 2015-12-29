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

<!-- END -->
