## GraphStream Talk & Demo 

Sources for the talks 

## Edit the slides

The sources of the slides are located in the ```app/``` folder. 


	app
	├── gs-base-tutorials.html
	├── gs-communities.html
	├── gs-lecture.html
	├── gs-netlogo.html
	├── images
	├── scripts
	└── styles

## Build the slides

### Node

First of all, you need the ```node``` javascript serverside interpretter installed on your system to build the project. This repends on your system. 

### Grunt & Bower

Second, if not already installed you need ```grunt``` and ```bower```:

```
npm install -g yo grunt-cli bower
```

### This talk's dependencies

Get al the dependencies resolved:

```
npm install && bower install 
```

### Build the project

```
grunt build
```

Then an optimised and compressed version of the slides should be available unr the ```dist``` folder.

### Run the talk locally

```
grunt server
```

