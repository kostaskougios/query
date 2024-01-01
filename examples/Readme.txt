run:

# create sample tables with data into data folder
./sampledata.sc
# start a repl to query the sample data
./example.sc

In the repl type
 - ? to view all available
 - select * from tweets limit 5
 - select count(*) from tweets
 - desc tweets
 - and other spark commands
 - press <tab> to autocomplete a keyword or table or column name

Autocomplete works by pressing tab, i.e. type sel<tab> (will complete to select) or twe<tab> (will complete to tweet, one of the sample tables)
or na<tab> (will complete to "name", a column name of the table tweet).

Copy example.sc and customize it to mount and use your tables.
