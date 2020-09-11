# Query
Query is a tool to view big data files (avro, parquet, orc, csv and all spark supported formats) as a database and query
them from an interactive console or scala ammonite scripts. It supports syntax colouring and basic autocomplete. 

![example 1](etc/img/ss1.png)
![example 1](etc/img/ss2.png)

# Install on linux 64 bit

This will create a self-contained folder with the jdk, ammonite and the query tool. 

- download and unzip https://drive.google.com/uc?id=14ORaIU1mW-CbLSwySY7SNEuzMhir3FcK&export=download (open with browser)

```
cd UNZIPPED_FOLDER
bin/install

# now ready to run scripts
bin/amm example.sc
```

# Install on other platforms

- Install jdk 8 or 11 and ammonite for scala 2.12 from https://ammonite.io/#OlderScalaVersions
- download and unzip https://drive.google.com/file/d/14ORaIU1mW-CbLSwySY7SNEuzMhir3FcK/view?usp=sharing (open with browser). Follow the instructions of the Readme file inside the zip file.

Known issues: 
- Autocomplete is basic, only table names and some sql keywords will be completed, also when pressing <tab> make sure to have a standalone
word (spaced from parenthesis, dots etc)

# Build from source

- git clone https://github.com/kostaskougios/query.git
- install mill from http://www.lihaoyi.com/mill/
- run `bin/prepare-distro`
- `cd dist && ls` 
