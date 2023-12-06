import {useEffect, useState} from "react";
import CarTable from "../../Components/CarTable/CarTable.jsx";
import Form from "../../Components/Form/Form.jsx";
import {currentDate} from "../../Utilities/CurrentDate.js";
import {initializeInputFieldsForKalender} from "./initializeInputFieldsForKalender.js";
import SelectElement from "../../Components/SelectElement/SelectElement.jsx";
import {fetchAuthenticated} from "../../Utilities/api.js";

const CarSearch = () => {

    const inputStartDateEndDateAndLocation = {
        startDate: "",
        endDate: "",
        location: ""
    }
    const [startDateEndDateAndLocation, setStartDateEndDateAndLocation] = useState(inputStartDateEndDateAndLocation);
    const [carHouses, setCarHouses] = useState([]);
    const [carSearch, setCarSearch] = useState(true);
    const [cars, setCars] = useState(null);
    const [invalidDate, setInvalidDate] = useState(false);
    const inputFieldsKalender = initializeInputFieldsForKalender(startDateEndDateAndLocation.startDate, startDateEndDateAndLocation.endDate);

    let userData = localStorage.getItem("userdata")
    let UserDataParsed;
    let userID;
    if (userData === null) {
        let userData = '{"userId": ""}';
        UserDataParsed = JSON.parse(userData)
        userID = UserDataParsed.userId;
    } else {
        UserDataParsed = JSON.parse(userData)
        userID = UserDataParsed.userId;
    }


    useEffect(() => {

        (async () => {
            try {
                const listOfCarHouseNames = await fetchAuthenticated("/carhouses/get-carhouse-names", {
                    method: "GET"
                });
                if (listOfCarHouseNames.ok) {
                    const listOfCarHousesParsed = await listOfCarHouseNames.json()
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
            const availableCars = await fetchAuthenticated(`/cars/find-available-cars-for-rent-by-name/${startDateEndDateAndLocation.location}/${startDateEndDateAndLocation.startDate}/${startDateEndDateAndLocation.endDate}`, {
                method: "GET"
            });
            if (availableCars.ok) {
                const availableCarsParsed = await availableCars.json()
                setCars(availableCarsParsed);
                setCarSearch(false);
            }
        } catch (err) {
            console.log(err)
        }
    }


    const startDateEndDateLocationHandler = (event) => {
        if (event.target.name === "startDate" || event.target.name === "endDate") {
            if (event.target.value < currentDate) {
                setInvalidDate(true);
                setTimeout(() => {
                    setInvalidDate(false);
                }, 2000)
                return;
            } else {
                console.log("VALID")
            }
        }
        setStartDateEndDateAndLocation({
            ...startDateEndDateAndLocation,
            [event.target.name]: event.target.value,
        });
    }


    if (carSearch) {
        return (
            <>
                {invalidDate ? <div>INVALID DATE: Time shouldn`t be in the past</div> : ""}
                <Form handleSubmit={handleSubmit}
                      inputFields={inputFieldsKalender}
                      onChangeHandler={startDateEndDateLocationHandler}
                      buttonValue={"Check Cars"}
                      disabled={startDateEndDateAndLocation.location.length <= 0 ||
                          startDateEndDateAndLocation.startDate.length <= 0 ||
                          startDateEndDateAndLocation.endDate.length <= 0}

                >

                    {<SelectElement
                        elementArray={carHouses}
                        onChangeHandler={startDateEndDateLocationHandler}
                        defaultValue={"Select Location"}
                        name={"location"}
                    />}
                </Form>
            </>
        );
    } else {
        return <CarTable
            cars={cars}
            startDate={startDateEndDateAndLocation.startDate}
            endDate={startDateEndDateAndLocation.endDate}
            userID={userID}
        />
    }
}
export default CarSearch;