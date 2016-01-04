---
title: GraphStream Tutorials
author:
- Let's get some coding done
date: NetSciX2016 -- School of Code
center: 1
controls: 0
height: 800
hideAddressBar: true
history: true
keyboard: true
loop: false
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



# Outlook

- Installation of GraphStream

- Tutorial 1: Basic tasks with GraphStream

- Tutorial 2: A first dynamic graph


# Installation of GraphStream

Basic steps to install GraphStream

- Get a working java SDK installed
- Get the GraphStream tutorial sources
- Get Eclipse installed
- Get GraphStream binaries



# Get the tutorial workspace
- Go to the tutorial page at github:
http://github.com/graphstream/gs-talk/
- Get the code:
    - with the "Download Zip" button on github (will download an archive)
    - or through git:

```sh
git clone git://github.com/graphstream/gs-talk.git

```

In that project, we want the `Demos/` folder.



# Get Eclipse Installed

- Go to the Eclipse download page at:
[www.eclipse.org/downloads/packages/eclipse-ide-java-developers/mars1](http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/mars1)
- On the right column, "Download Links", pick up your OS
- Click the download button
- Once downloaded, open the file. An eclipse folder is created. Move it somewhere safe.
- Start Eclipse.


# Import the project

- When asked about a workspace, indicate a new workspace or any existing one.
- Go to menu `File > Import > Maven > Existing Maven Projects`.
Browse to the project (`Demos/` folder), Eclipse should recognize the project.

![eclipse-import-maven-projects](img/eclipse-import-maven-projects.png)




# Get GraphStream binaries
### With Maven
- Dependencies are automatically resolved by maven in the `pom.xml` file :

```xml
<!-- ... -->
<groupId>org.graphstream</groupId>
<artifactId>gs-core</artifactId>
<version>1.3</version>
<!-- ... -->
<groupId>org.graphstream</groupId>
<artifactId>gs-algo</artifactId>
<version>1.3</version>
<!-- ... -->
<groupId>org.graphstream</groupId>
<artifactId>gs-ui</artifactId>
<version>1.3</version>
```
- In Eclipse : nothing to do!
- Or on the command line : `mvn compile`

# Get GraphStream binaries
### Without Maven
Binaries can be found on the GraphStream Download page  [graphstream-project.org/download/](http://graphstream-project.org/download/) :

- `gs-algo-1.3.zip`, `gs-core-1.3.zip`, `gs-ui-1.3.zip`.
- Extract those zip file, and copy `gs-algo-1.3.jar`, `gs-core-1.3.jar`, `gs-ui-1.3.jar` to the `gs-talk-NetSciX2016/Demos/lib` folder of the project.
- In Eclipse, select the project in the left panel and refresh the project (F5 key).
- Right-clic on the project (`gs-talk`), then properties, then Java build path (left panel), then libraries (right panel), then clic the `Add Jars` button, and select our 3 jar files in the `gs-talk-NetSciX2016/Demos/lib/` folder.


#Tutorial 1


###Basic tasks with GraphStream

#Create and display

- Open
`src/org/graphstream/demo/tutorial1/Tutorial1.java`
- Right-clic on `Tutorial1.java`, then `Run As > Java Application`

```java
public class Tutorial1 {
	public static void main(String args[]) {
		Graph graph = new SingleGraph("Tutorial 1");
		graph.display();
		graph.addNode("A");
		graph.addNode("B");
		graph.addEdge("AB", "A", "B");
		graph.addNode("C");
		graph.addEdge("BC", "B", "C", true); // Directed edge.
		graph.addEdge("CA", "C", "A");
	}
}
```
Maven command line:
`mvn exec:java-Dexec.mainClass="org.graphstream.demo.tutorial1.Tutorial1"`

#Change the Display with CSS

We can improve the display with some CSS:

```java
...
graph.display();

graph.addAttribute("ui.quality");
graph.addAttribute("ui.antialias");
graph.addAttribute("ui.stylesheet", "edge { fill-color: grey; }");

graph.addNode("A");
...
```

#Access Elements

- Each node, edge and attribute is identified by an unique string.
- The node and edge elements are created for you.
- You can access them however, when created: `Node n = graph.addNode("A");`
- Or after creation: `Node n = graph.getNode("A");`

#Constructive API vs. Events

- You can remove nodes and edges the same way: `graph.removeNode("A");`
- You can change the graph this way at any time. Each change is considered as an “event”.
- The sequence of changes is seen as the dynamics of the graph.
- There are many other ways to modify the graph.

#Attributes
Data stored in the graph, on nodes and edges, are called “attributes”.
An attribute is a pair (name,value).

```java
Edge ab = graph.getEdge("AB");
Edge bc = graph.getEdge("BC");
Edge ca = graph.getEdge("CA");

ab.setAttribute("ui.label", "AB");
bc.setAttribute("ui.label", "BC");
ca.setAttribute("ui.label", "CA");

```


#Define Attributes

- But you can add any kind of data on each graph element.
- However not all attributes appear in the viewer.
- Notice the way you can add arrays with `setAttribute()` and a variable number of arguments:

```java
ab.setAttribute("aNumber", 10);
bc.setAttribute("anObject", new Double(10));
ca.setAttribute("anArrayOfThings", 1, 2, 3);
```

#Retrieve Attributes

- Several ways to retrieve attributes:

```java
int value1 = ((Number) ab.getAttribute("aNumber")).intValue();
double value2 = bc.getAttribute("anObject");
Object[] value3 = ca.getAttribute("anArrayOfThings");

```
Special methods are here to simplify things:

```java
double value4 = ab.getNumber("aNumber");
double value5 = bc.getNumber("anObject");

```


#Traversing the graph
Iterating through all nodes of the graph is easy:
```java
for(Node n: graph) {
	System.out.println(n.getId());
}

```
Equally for edges:
```java
for(Edge e: graph.getEachEdge()) {
	System.out.println(e.getId());
}

```


#Other iterators
Iterators for nodes:
```java
Iterator<? extends Node> nodes = graph.getNodeIterator();

while(nodes.hasNext()) {
	System.out.println(nodes.next().getId());
}

```

Iterators for edges:

```java
Iterator<? extends Edge> edges = graph.getEdgeIterator();

while(edges.hasNext()) {
	System.out.println(edges.next().getId());
}		
```


#Index-based  access

Indices for nodes:

```java
int n = graph.getNodeCount();
for(int i=0; i<n; i++) {
	System.out.println(graph.getNode(i).getId());
}

```

Indices for edges:

```java
int n = graph.getEdgeCount();
for(int i=0; i<n; i++) {
	System.out.println(graph.getEdge(i).getId());
}

```
⚠ indices remain the same as long as the graph is unchanged. But as soon as an addition or removal occurs, indices are no longer tied to their old node or edge ⚠



#Travers from nodes and edges

You can also travel the graph using nodes:

```java
import static org.graphstream.algorithm.Toolkit.*;
//...
Node node = randomNode(graph);

for(Edge e: node.getEachEdge()) {
	System.out.printf("neighbor %s via %s%n",
		e.getOpposite(node).getId(),
		e.getId() );
}

```


- Each node and edge allow to iterate on their neighbourhood.
- `Toolkit` is set of often used functions and small algorithms (see the [API](http://www.graphstream-project.org/api/gs-algo/org/graphstream/algorithm/Toolkit.html)).


# Orientation-based interaction
You can iterate on directed edges:

```java
Node node = getRandomNode(graph);
Iterator<? extends Edge> edges = node.getLeavingEdgeIterator();
```

Or:
```java
Iterator<? extends Edge> edges = node.getEnteringEdgeIterator();
```
And get the node degree, entering or leaving:

```java
System.out.println(“Node degree %d (entering %d, leaving %d)%n”,
	node.getDegree(),
	node.getInDegree(),
	node.getOutDegree());

```

#Tutorial 2


###A first dynamic graph

#Sinks
- A graph can receive events. It is a _sink_.
- A _sink_  is connected to a _source_ using the `Source.addSink(Sink)` method.
- Events are filtered by type (_Elements Events_ and _Attributes Events_) :
    - `addElementSink(ElementSink)`.  Nodes and edges are _Elements_.
    - `addAttributeSink(AttributeSink)`. Data attributes are stored on every element.
- A `Sink` is both an `ElementSink` and `AttributeSink`.



#ElementSink
ElementSink is an interface
```java
public interface ElementSink {
	void nodeAdded( ... );
	void nodeRemoved( ... );
	void edgeAdded( ... );
	void edgeRemoved( ... );
	void graphCleared( ... );
	void stepBegins( ... );
}
```


#AttributeSink
An attribute sink must follow the interface:

```java
public interface AttributeSink {
	void graphAttributeAdded( ... );
	void graphAttributeChanged( ... );
	void graphAttributeRemoved( ... );

	void nodeAttributeAdded( ... );
	void nodeAttributeChanged( ... );
	void nodeAttributeRemoved( ... );

	void edgeAttributeAdded( ... );
	void edgeAttributeChanged( ... );
	void edgeAttributeRemoved( ... );
}
```

#Source
A source is an interface that only defines methods to handle a set of sinks.

```java
public interface Source {
	void addSink(Sink sink);
	void removeSink(Sink sink);
	void addAttributeSink(AttributeSink sink);
	void removeAttributeSink(AttributeSink sink);
	void addElementSink(ElementSink sink);
	void removeElementSink(ElementSink sink);
	void clearElementSinks();
	void clearAttributeSinks();
	void clearSinks();
}

```

#A first dynamic graph

Since Graph is a _sink_ let's create a graph from a set of events generated by a _source_

- A file with stored information about graphs (with a proper file format) can be a source of events
- A few graph file formats can handle dynamic.
- GraphStream provides a file format (DGS) that allows to store and load dynamic graphs.

![A graph from a file](img/pipeline.svg)

# The GDS File Format

- `an` for "add node"
- `ae` for "add edge"
- `ae "AB" "A" > "B"` adds a directed edge between nodes `A` and `B`.
- `cn`, `ce` and `cg` change or add one or more attributes on a node, an edge or the graph.
- `dn` and `de` allow to remove nodes, edges.


Open the example DGS file in  `data/tutorial2.dgs`

```
DGS003
"Tutorial 2" 0 0
an "A"
an "B"
an "C"
ae "AB" "A" "B"
ae "BC" "B" "C"
ae "CA" "C" "A"
```


# How to handle dynamics
- Storing temporal information is tricky.
- Timestamps on events is a way good way to encode time
- But some events occur at the same time.
- Let's define _time steps_ within events
- `st <number>`

# Steps in DGS

The ability to remove nodes and edges make the format dynamic.

Add this to the  `data/tutorial2.dgs`  file:
```
st 2
an "D"
an "E"
ae "BD" "B" "D"
ae "CE" "C" "E"
ae "DE" "D" "E"
de "AB"
dn "A"
st 3
```
And save it.
