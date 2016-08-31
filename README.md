ROOMS
=====
This a simple single-player game.  

##Design description
- Essential types: `Context`, `Room`, `Decision`, Interactable, `Creautre`, `Player`, and `Console`.
- There is always a `Context`. A context can be the starting menu, a room in which player might fight, etc.  
- In every context, there is at least one `Decision` available. A decision can be 'Load Game', 'Open Door', 
'Fight Beast', etc.  
- In some context (e.g. `Room`), there might be some `Interactable` objects with at least one decision, 
which is added to the list of decisions of the context.  
- The player needs to select a decision from the available ones.  
- A decision, to be made, might need some arguments which need to be provided by the user.  
- Making a decision has a `Consequence` comprising of a message and a new context, which can be the same as previous context or totally a new one.  
- The game continues util there is no decision in the current context.  

##Design features
- Readable persistence format makes it convenient to generate new games with more features (e.g. more rooms).
- The design has full separation of concerns: main packages 
are `context`, `decision`, `intraction`, `player`, `ui`.  
- Maximum of extensibility is provide via component-oriented design.
 Every component works only with interfaces of others.  
- To use different user interface, add a new implementation of `Console` interface.  
- To add more entities (creatures in rooms), extend concrete implementations 
(`Creature`) or add a new implementation of `Interactable` interface.  
- To add more interaction options to `Creature` simply add more `Decision` implementations to `listDecisions` method.  
- To add more interesting players, extend `SimplePlayer` or add a new implementation of `Player` interface.  
- There are 43 test cases and more than 90% test code coverage.  
  

##Other notes
- Too many classes and too much abstraction are for demonstration purpose.  
- To build the package (`jar`) by the following: `mvn clean package`.  
- The game can easily be executed by the following: `java -jar rooms.jar`.  

##User stories:
- As a player I want to create a character. It's demonstrated via asking for player name.  
- As a player I want to explore. It's demonstrated via moving from one context (e.g. room) to another.  
- As a player I want to gain experience through fighting. It's demonstrated via fighting with creatures in rooms.  
- As a player I want to save and resume a game. It's demonstrated via pausing, saving, and loading game.