import {useEffect, useState} from "react";
import {jswTokenFetch} from "../Utilities/jswTokenFetch.js";
import Loading from "../Components/Loading/Loading.jsx";
import CarTable from "../Components/CarTable.jsx";
import SubmitButton from "../Components/SubmitButton.jsx";

const CarSearch = () => {
    const [carHouses, setCarHouses] = useState([]);
    const [location, setLocation] = useState("");
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [carSearch, setCarSearch] = useState(true);
    const [cars, setCars] = useState(null);
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, "0");
    const mm = String(today.getMonth() + 1).padStart(2, "0"); // January is 0!
    const yyyy = today.getFullYear();
    const currentDate = yyyy + "-" + mm + "-" + dd;


    useEffect(() => {

        (async () => {
            try {
                const headers = {
                    "Content-Type": "application/json"
                };

                const listOfCarHouseNames = await jswTokenFetch("/carhouses/get-carhouse-names", {}, headers);

                if (listOfCarHouseNames.ok) {
                    const listOfCarHousesParsed = await listOfCarHouseNames.json()
                    console.log(listOfCarHousesParsed)
                    setCarHouses(listOfCarHousesParsed);
                }
            } catch (err) {
                console.log(err)
            }
        })()

    }, []);


    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const headers = {
                "Content-Type": "application/json"
            };

            const availableCars = await jswTokenFetch(`/cars/find-available-cars-for-rent-by-name/${location}/${startDate}/${endDate}`, {}, headers);

            if (availableCars.ok) {
                const availableCarsParsed = await availableCars.json()
                setCars(availableCarsParsed);
                setCarSearch(false);
            }
        } catch (err) {
            console.log(err)
        }
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

    if (carSearch) {
        return (
            <form onSubmit={handleSubmit}>
                <label htmlFor="location">Select a location:</label>
                <select id="location" name="location" onChange={onCarHouseHandler} defaultValue="">
                    <option value="" disabled>Select a location</option>
                    {carHouses.map((value, index) => {
                        return <option key={index} value={value}>{value}</option>
                    })}
                </select>
                <label htmlFor="start">Start date:</label>
                <input type="date" id="start" name="trip-start" onChange={startDateHandler} value={startDate}
                       min={currentDate}/>
                <label htmlFor="end">End date:</label>
                <input type="date" id="end" name="trip-send" onChange={endDateHandler} value={endDate}
                       min={currentDate}/>
                <input type="submit" value={'Show cars'}
                       disabled={location.length <= 0 || startDate.length <= 0 || endDate.length <= 0}/>
            </form>
        );
    } else {
        return <CarTable cars={cars} startDate={startDate} endDate={endDate}/>
    }
}
export default CarSearch;