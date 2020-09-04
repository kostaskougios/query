# Download

- Install ammonite for scala 2.12 from https://ammonite.io/#OlderScalaVersions
- download and unzip https://drive.google.com/file/d/14ORaIU1mW-CbLSwySY7SNEuzMhir3FcK/view?usp=sharing (open with browser). Follow the instructions of the Readme file inside the zip file.

Known issues: 
- Autocomplete is basic, only table names and some sql keywords will be completed, also when pressing <tab> make sure to have a standalone
word (spaced from parenthesis, dots etc)

# Build from source

- git clone https://github.com/kostaskougios/query.git
- install mill from http://www.lihaoyi.com/mill/
- run `bin/prepare-distro`
- `cd dist && ls` 
