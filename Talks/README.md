# GraphStream Talk & Demo

Sources for the talks

## Edit the slides

The sources of the slides are located in the `src/` folder.


``` sh
src
├── *.md
├── img/
├── fonts/
└── css/

```

## Dependencies


### pandoc
This project uses the [pandoc](http://pandoc.org) document convertor in order to created [rejeal.js](http://lab.hakim.se/reveal-js/) slideshows from markdown documents.

You need `pandoc` installed on your system first.

### node

 the [node.js](nodejs.org) javascript server-side interpreter is used to automate the build. You need `node.js` in your system to build the project.

## Build the slides
Once node is installed run the build command that will install all the server-side and client-side dependencies and then build the web site in the `app/` folder.

```sh
npm run build
```


A local server can be run locally with the slides. Run the following command:

```sh
npm run serve
```

This will serve the web site locally and reload it after any change in the source code.
