A graphical tool for creating and editing Pentaho Action Sequences.  Action Sequence (.xaction) XML files are conventionally either created by hand or by the Pentaho Design Studio Eclipse plugin.  Pentaho ASD is a web-based tool (plugin to PUC) that allows you to visually create a BI workflow using the Actions at your disposal as part the Pentaho BIServer toolkit.

## History ##
This project was developed as a prototype during the Pentaho developer fun week 2010 by Aaron (UI work) and Angelo (action sequence model work).

## Continuous Integration ##
Pentaho's Hudson continuous integration hosts the CI build @
http://ci.pentaho.com/job/pentaho-asd/

## People ##
project founder: aaron
twitter: @phytodata
IRC nick: phyto  (lurks about in ##pentaho)

## Instructions ##
Currently, this project is more or less a prototype of what is possible as far as designing action sequences.  To run this project, do the following:
  1. download the source
  1. run "ant resolve" (this will resolve all dependent jars and update your eclipse classpath)
  1. install the GWT plugin for Eclipse.  It must be a recent one, that comes with GWT 2.X
  1. Run in GWT 2.0 hosted mode. In Eclipse go to "Run As->[GWT](GWT.md) Web Application"
  1. The Eclipse GWT plugin will direct you to an URL.. launch that in FF
  1. Play with the UI

Of course you do not need to use Eclipse.  You can launch GWT hosted mode in other ways.

## Screen Shots ##
This screenshot illustrates the workflow nature of the UI.  You will select a BI action (e.g. SQL Lookup Rule), drag it around on the pallet and connect it to other actions.
![http://pentaho-asd.googlecode.com/svn/wiki/images/asd1.png](http://pentaho-asd.googlecode.com/svn/wiki/images/asd1.png)

The XML tab shown below is an illustration of the generation of .xaction file content.  I hope to see the .xaction written to the solution repository and the user being able to test/execute the action sequence from within the editor.
![http://pentaho-asd.googlecode.com/svn/wiki/images/asd2.png](http://pentaho-asd.googlecode.com/svn/wiki/images/asd2.png)