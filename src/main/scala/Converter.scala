import java.io._
import org.apache.spark.sql.SparkSession

object Converter {

    def convert(spark: SparkSession, csvfile: String, parqfile: String, schemafile: String, sep: String = ",", reparts: Int = 0) {

	//var df = spark.read.format("com.databricks.spark.csv") //spark2.0
        var df = spark.sqlContext.read.format("csv").option("header", "true").option("sep", sep).option("inferSchema", "true").load(csvfile)
	//.option("wholeFile", true) -- doesnt work

        //df.printSchema()
	//println(df.schema)
	// todo assert df.columns == x

	// to control the number of partitions written on standalone single node cluster
	if (reparts > 0) {
	    df = df.repartition(reparts)
	}

	// text schema dump
	val out = new PrintWriter(new FileWriter(schemafile))
	df.schema.foreach(e => out.println(e))
	out.close()

	// parquet
	df.write.parquet(parqfile)

	// todo asserts
	//val dfp = sqlContext.read.format("parquet").load(parqfile)
	//df.columns == x

	// binary schema dump
	//import java.io._
	//val oos = new ObjectOutputStream(new FileOutputStream(schemafile))
	//oos.writeObject(df.schema)
	//oos.close
    }

    def main(args: Array[String]) {
	// spark session from Spark 2.0: https://databricks.com/blog/2016/08/15/how-to-use-sparksession-in-apache-spark-2-0.html
	val spark = SparkSession
	    .builder()
	    .appName("Csv2ParquetConverter")
   	    .getOrCreate()

	// you can set conf params like this.. or to spark-submit...
	//spark.conf.set("spark.executor.memory", "10g")
	//spark.conf.set("spark.sql.parquet.compression.codec", "uncompressed") // for dask...

	// todo array index check etc
	convert(spark, args(0), args(1), args(2), 
		sep=if (args.length>4) args(4) else "\t", 
		reparts=if (args.length>3) args(3).toInt else 0) 

    }

}


