<!-- # Iteration 1

### Features -->

<!-- - Creating recipes
  - As an user, I can add a picture to each of my cooking step because it would be easier for other people to follow my recipe. 
- Grouping recipes (Create folders to group them)
- Searching recipes
  - Search by _verified_ nutrition value
  - Search by group (appetizer, dinner, etc.)
- Sharing recipes
- Saving/favouriting recipes
- Plan meals for each day
  - Calendar
- Checklist for ingredients -->

<!-- - Make a profile
  - As a user, I want to be able to add my favorite types of food to my profile.
  - As a user, I want to be able to access to my profile through my phone anywhere or my tablet at home.
- Create recipes
  - As someone creating a recipe, I want to be able to add a picture of what the dish looks like so that it's easier to find it at a glance.
  - As someone making recipes, I need to add ingredients to my recipe, so that people following the recipe know what ingredients are needed.
  - As someone creating a recipe, I need to add steps to the recipe, so that people following the recipe always know what to do next and when to use a particular ingredient.
  - As someone creating a recipe, I want to add pictures to the steps of my recipe so that it is easier to follow.
- Save/Favorite Recipes
  - As a user, I want to be able to keep a list of my favorite recipes so I can easily find a dish when I want to make it again.
  - As a user, I want to save recipes in different groups based on their food types. -->


# Iteration 1

## This Iteration

[Demo](https://www.youtube.com/watch?v=6X8gk-gXtco)

### Features
Finished:
- make an online profile
- make a recipe (backend side)

Started but not Finished:
- let user create custom recipes
   
### User Stories
Finished:
- login and signup functions
- create and save profile on online database (firebase)
- view lists of recipes

Started but Not Finished:
- add steps
- add picture for step

### Changes Made since Iteration 0
- No longer implementing multiple profiles for a single account
- Spoke about adding tags to recipes in order to categorize and sort by tags
- Implementing ingredients and instructions as strings rather than individual objects for each ingredient/instruction

### Architecture Document
We updated our [Architecture Document](ArchitectureDocumentation.md)

## Next Iteration
In the second Iteration we would like to :
- add tests since we had trouble with dependencies and duplicated classes from junit after merging
- add option for user to post, edit and delete recipes
- add saving/favorite recipe function
- add search and view personal profile options
- create a proper database for recipes, tags and ingredients
- reorganize java files as well as refactor some codes
