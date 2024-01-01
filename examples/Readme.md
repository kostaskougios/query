run:
```shell
# create sample tables with data into data folder
./sampledata.sc
# start a repl to query the tweet sample data only
./tweets.sc
```

In the repl type
 - ? to view all available
 - ?select to view sample select statements for each table so that you can copy-paste
 - select * from tweets limit 5
 - select count(*) from tweets
 - desc tweets
 - and other spark commands
 - press <tab> to autocomplete a keyword or table or column name

Autocomplete works by pressing tab, i.e. type sel<tab> (will complete to select) or twe<tab> (will complete to tweet, one of the sample tables)
or na<tab> (will complete to "name", a column name of the table tweet).

See `full-example.sc` for a full example of what you can do when mounting tables which mounts all example tables.

Copy `full-example.sc` and customize it to mount and use your tables.
