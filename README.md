# Network Project

## Testing the Project
To run a game of Network against the coded AI just run *new_game* from the root directory. For Windows, use the *.bat* file, for Mac use the *.sh* file.

## Using GitHub with Eclipse
Figuring out how to use GitHub with Eclipse took me a little while so I thought I'd write it out here.

### Installing GitHub for Eclipse
1. Go to <code>Help</code> > <code>Install New Software...</code>
2. Under *Work with:* select "Kepler"
3. Where it says *type filter text*, type in "git"
4. Select "Eclipse GitHub integration with task focused interface", "Java implementation of Git - optional Java 7 libraries", and "Mylyn Versions Connector: Git"
5. Press the *Finish* button

### Cloning this project
1. Select <code>File</code> > <code>Import...</code>
2. Under *Git*, select "Projects from Git" and click *Next >*
3. Select "Clone URI" and click *Next >*
4. Under *Host:*, type "github.com" and under *Repository path:*, type "rtadinada/cs61b_pj2.git"
5. In the dropdown next to *Protocol:*, select *https*
6. Type in your username and password under *Authentication* and hit *Next >*
7. Make sure "master" is selected and hit *Next >* again
8. Choose where you want to save the project and *Next >*
9. Make sure "Import existing projects" is selected and hit *Next >* again
10. Select "pj2" and hit *Finish*

### Committing, pushing, and pulling
* Committing and pushing
	1. Right click on pj2 and select <code>Team</code> > <code>Commit...</code>
	2. Under *Commit message* add an appropriate message about the changes made
	3. Under *Files*, make sure all the files you want to commit are selected
	4. Hit *Commit and Push*
* Pulling
	1. Right click on pj2 and select <code>Team</code> > <code>Fetch from Upstream</code> and hit *OK*
	2. Right click on pj2 and select <code>Team</code> > <code>Merge</code> and hit *Merge*