rm -rf output

java -cp .:lib/* com.godmonth.memstat.Show $1 $2

tar czvf output.tgz output
