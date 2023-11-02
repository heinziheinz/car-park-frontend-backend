import {useEffect, useState} from "react";
import {jswTokenFetch} from "../Utilities/jswTokenFetch.js";

const CarSearch = () => {
    const [carHouses, setCarHouses] = useState([]);



    useEffect(() => {

        (async () => {
            try {
                //TODO: doesn`t work
                console.log("HAlllllo")
                const headers = {
                    "Content-Type": "application/json" };

                const listOfCarHouseNames = await jswTokenFetch("/carhouses/get-carhouse-names", {}, headers);
                console.log("HAllllloOOOSOSOSO")
                 console.log(listOfCarHouseNames)
                if(listOfCarHouseNames.ok){
                    const listOfCarHousesParsed = await listOfCarHouseNames.json()
                    setCarHouses(listOfCarHousesParsed);
                }
            } catch (err) {
console.log(err)
            }

        })()


    }, []);


    const [location, setLocation] = useState("");
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");

    // Get today's date in the format "YYYY-MM-DD"
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, "0");
    const mm = String(today.getMonth() + 1).padStart(2, "0"); // January is 0!
    const yyyy = today.getFullYear();
    const currentDate = yyyy + "-" + mm + "-" + dd;
  //  const carHouses = ["Wien Westbahnhof", "Berlin Flughafen", "München Flughafen", "Dresden Flughafen"];


    const handleSubmit = async (event) => {
        event.preventDefault();
        console.log("Submit")
    }
    const onCarHouseHandler = (event) => {
        setLocation(event.target.value)
    }

    const startDateHandler = (event) => {
        setStartDate(event.target.value)
    }

    const endDateHandler = (event) => {
        setEndDate(event.target.value);
    }

    return (
        <form onSubmit={handleSubmit}>
            <label htmlFor="location">Select a location:</label>
            <select id="location" name="location" onChange={onCarHouseHandler}>
                {carHouses.map((value, index) => {
                    return <option key={value}>{value}</option>
                })}
            </select>

            <label htmlFor="start">Start date:</label>
            <input type="date" id="start" name="trip-start" onChange={startDateHandler} value={currentDate}
                   min={currentDate}/>
            <label htmlFor="end">End date:</label>
            <input type="date" id="end" name="trip-send" onChange={endDateHandler} value={currentDate}
                   min={currentDate}/>
            <input type="submit" value={'Show cars'}/>
        </form>
    );
}
export default CarSearch;