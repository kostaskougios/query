Please install ammonite for scala 2.12 from :

https://ammonite.io/#OlderScalaVersions

then as an example run:

# create sample tables into data folder
amm sampledata.sc
# start a repl to query the sample data
amm example.sc

In the repl type ? for help or a query like:
select * from tweets limit 5

Autocomplete works by pressing tab, i.e. type sel<tab> (will complete to select) or twe<tab> (will complete to tweet, one of the sample tables)
or na<tab> (will complete to "name", a column name of the table tweet).

Copy example.sc and customize it to mount and use your tables.
