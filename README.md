Simple standalone Scala Spark application to convert CSV to Parquet files
 

### Installation

Install Spark on Mac: https://medium.freecodecamp.org/installing-scala-and-apache-spark-on-mac-os-837ae57d283f

plus

```
brew install sbt
```
### Run

```
# build 
sbt build;

# run local spark
spark-submit \
    --class Converter \
    --master local[4] \
    target/scala-2.11/csv2parquet_2.11-1.0.jar <in_csvfile> <out_parquetfile> [out_schemafile] [num_partitions] [sep]

```

### References

#### Spark resource tuning
From [cldr]
* master[workers] - the number of worker threads started (see [submit])
*`spark.executor.memory` and `spark.executor.cores` - memory and cores available to each executor
* (automated >spark1.3) `spark.executor.instances` - number of executors for an application. executors run on workers
	

#### Spark refs

* https://spark.apache.org/docs/latest/quick-start.html#self-contained-applications
* https://docs.databricks.com/spark/latest/data-sources/read-csv.html#manipulating-data followed by  
* https://databricks.com/blog/2016/08/15/how-to-use-sparksession-in-apache-spark-2-0.html
* [cldr] https://blog.cloudera.com/blog/2015/03/how-to-tune-your-apache-spark-jobs-part-2/
* [submit] https://spark.apache.org/docs/latest/submitting-applications.html

#### Scala beginner refs

* Option[String]: http://danielwestheide.com/blog/2012/12/19/the-neophytes-guide-to-scala-part-5-the-option-type.html
