---
title: GraphStream Tutorials
author:
- Let's get some coding done
date: Formations JEDI -- April 21 2016
center: 1
controls: 0
height: 800
hideAddressBar: true
history: false
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



# Outline
<!--  {data-background="url('/img/tmp.png')"} -->


- [General Presentation of GraphStream](index.html)
- First Tutorials (this presentation)
- [Community Structures Tutorial](communities.html)



# Installation of GraphStream

Basic steps to install GraphStream

- Get a working java SDK installed
- Get the GraphStream tutorial sources
- Get Eclipse installed
- Get GraphStream binaries



# Get the tutorial workspace
- Go to the tutorial page at github:
[github.com/graphstream/gs-talk/tree/NetSciX2016](https://github.com/graphstream/gs-talk/tree/NetSciX2016)
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
graph.setAttribute("ui.stylesheet", "" +
                    "edge {" +
                    "   size: 2px;" +
                    "   fill-color: gray;" +
                    "}");

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

for(String id : new String[]{"A", "B", "C"}){
    graph.getNode(id).setAttribute("ui.label", id);
}
graph.setAttribute("ui.stylesheet", "url(data/style.css);");

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

Several ways to retrieve attributes:

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

Since Graph is a _sink_ let's create a graph from a set of events generated by a _source_.

- A file with  information about graphs (in a proper file format) can be a source of events.
- A few graph file formats can handle dynamic.
- GraphStream provides a file format (DGS) that allows to store and load dynamic graphs.

![A graph from a file](img/pipeline.svg)

# The GDS File Format

- `an` for "add node".
- `ae` for "add edge".
- `ae "AB" "A" > "B"` adds a directed edge between nodes `A` and `B`.
- `cn`, `ce` and `cg` change or add one or more attributes on a node, an edge or the graph.
- `dn` and `de` allow to remove nodes, edges.


Open the example DGS file in  `data/tutorial2.dgs`.

```c
DGS003
"Tutorial 2" 0 0
an "A"
an "B"
an "C"
ae "AB" "A" "B"
...
```


# How to handle dynamics
- Storing temporal information is tricky.
- Timestamps on events is a  good way to encode time
- But some events occur at the same time.
- Let's define _time steps_ within events
- `st <number>`

# Steps in DGS

The ability to remove nodes and edges make the format dynamic.

Add this to the  `data/tutorial2.dgs`  file:
```c
st 2
an "D" label="D"
an "E" label="E"
ae "BD" "B" "D" label="BD"
ae "CE" "C" "E" label="CE"
ae "DE" "D" "E" label="DE"
st 3
de "AB"
st 4
dn "A"
```
And save it.

#Read the whole file

The file can be read entirely :

```java
graph.read("tutorial2.dgs");
```

- However this will send all events as fast as possible.
- We have no control over the speed at which events occur.


#Read the file event by event
We can read the DGS file event by event using an input source:
```java
Graph graph = new SingleGraph("Tutorial2");
graph.display();
FileSource source = new FileSourceDGS();
source.addSink( graph );
source.begin("data/tutorial2.dgs");
while( source.nextEvents() );
source.end();
```
![FileSource Pipeline](img/pipeline.svg)


#Read the file step by step
- We read the file event by event (line by line in the file), however it still does it as fast as it can.
- Note the line `while(source.nextEvents());`
- Also note that we have to call the `begin()` and `end()` methods before and after reading to cleanly open and close the file.
- Let's slow down the process :
``` java
while(source.nextEvents()) { Thread.sleep(1000); }
```
- We can also run it step by step so that events between two step appear together
```java
while(source.nextStep()) { Thread.sleep(1000); }
```


# Graph Layout
- By default the graph the spacial position of nodes on the display is automatically computed.
- However one may want to position nodes by ourself.
- One can do this using the `x` and `y` attributes:

```c
an "A" x=0 y=0.86
an "B" x=1 y=-1
an "C" x=-1 y=-1
// ...
an "D" x=1 y=1
an "E" x=-1 y=1
```
Then one have to tell the viewer  not to compute nodes positions:
```java
graph.display(false);
```
