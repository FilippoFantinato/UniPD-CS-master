import requests, time, datetime, random, json

rest_s_url = 'http://localhost:8888/api/sensors/add'

try:

    # get the new data value
    new_s_value = random.randint(1, 1000)
    tstamp = datetime.datetime.now().isoformat()
    theval = {
        "tstamp": tstamp,
        "label": "portenta_h7",
        "value": new_s_value,
        "unity": "mWatts"
    }

    resp = requests.post(rest_s_url,
        data=json.dumps(theval),
        headers={'Content-Type':'application/json'})
    if resp.status_code != 200:
        # This means something went wrong.
        raise ValueError

except ValueError:
    print("Bad reply from PUT")
