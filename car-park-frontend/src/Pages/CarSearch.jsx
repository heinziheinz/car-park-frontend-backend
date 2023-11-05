import {useEffect, useState} from "react";
import {jswTokenFetch} from "../Utilities/jswTokenFetch.js";
import Loading from "../Components/Loading/Loading.jsx";
import CarTable from "../Components/CarTable.jsx";
import SubmitButton from "../Components/SubmitButton.jsx";

const CarSearch = () => {

    const inputStartDateEndDateAndLocation = {
        startDate: "",
        endDate: "",
        location:""
    }
    const [startDateEndDateAndLocation, setStartDateEndDateAndLocation] = useState(inputStartDateEndDateAndLocation);
    const [carHouses, setCarHouses] = useState([]);
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
        console.log(inputStartDateEndDateAndLocation)
        try {
            const headers = {
                "Content-Type": "application/json"
            };

            const availableCars = await jswTokenFetch(`/cars/find-available-cars-for-rent-by-name/${startDateEndDateAndLocation.location}/${startDateEndDateAndLocation.startDate}/${startDateEndDateAndLocation.endDate}`, {}, headers);

            if (availableCars.ok) {
                const availableCarsParsed = await availableCars.json()
                setCars(availableCarsParsed);
                setCarSearch(false);
            }
        } catch (err) {
            console.log(err)
        }
    }

    useEffect(()=>{
        console.log("startDateEndDate");
        console.log(startDateEndDateAndLocation);
    }, [startDateEndDateAndLocation])

    const startDateEndDateHandler = (event) => {
        setStartDateEndDateAndLocation({
            ...startDateEndDateAndLocation,
            [event.target.name]: event.target.value,
        });
    }

    if (carSearch) {
        return (
            <form onSubmit={handleSubmit}>
                <label htmlFor="location">Select a location:</label>
                <select id="location" name="location" onChange={startDateEndDateHandler} defaultValue="">
                    <option value="" disabled>Select a location</option>
                    {carHouses.map((value, index) => {
                        return <option key={index} value={value}>{value}</option>
                    })}
                </select>
                <label htmlFor="start">Start date:</label>
                <input type="date" id="start" name="startDate" onChange={startDateEndDateHandler} value={startDateEndDateAndLocation.startDate}
                       min={currentDate}/>
                <label htmlFor="end">End date:</label>
                <input type="date" id="end" name="endDate" onChange={startDateEndDateHandler} value={startDateEndDateAndLocation.endDate}
                       min={currentDate}/>

                <SubmitButton value={"Submit"}
                              disabled={startDateEndDateAndLocation.location.length <= 0 || startDateEndDateAndLocation.startDate.length <= 0 || startDateEndDateAndLocation.endDate.length <= 0}/>
            </form>
        );
    } else {
        return <CarTable cars={cars} startDate={startDateEndDateAndLocation.startDate} endDate={startDateEndDateAndLocation.endDate}/>
    }
}
export default CarSearch;