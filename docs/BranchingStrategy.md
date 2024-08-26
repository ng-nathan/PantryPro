# Branching Strategy

Our branching strategy consists of having a development branch branching off from the main branch. In turn from this development branch feature branches will branch off, this helps to isolate the development of each feature. Finally, from these feature branches, we will have user story branches and, from those, several development task (dev_task) branches will branch off, this way a feature can be broken down into steps and these steps can be assessed independently from the others. Once a development task branch is finished it is merged into its parent user story branch which is then merged into the feature branch when finished and once all the children development task branches have been merged to its parent feature branch, the feature branch is merged to the development branch. The development branch will be merged to the main branch whenever there is a new release.

**In Short:**
- main (release branch)
    - dev
        - feature
            - user_story
                - dev_task
