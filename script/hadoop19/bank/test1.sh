TODAY_0D_F=$(date +%Y-%m-%d)
time1=$(date +%s -d"${TODAY_0D_F} 05:00:00")

time_exc=$((time1+1*24*60*60))
time_now=$(date +%s)

time_sleep=$((time_exc-time_now))


echo ${time_sleep}s
echo $((time_sleep/3600))
