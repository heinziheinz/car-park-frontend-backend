import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {jwtTokenFetch} from "../Utilities/jwtTokenFetch.js";
import {fetchAuthenticated} from "../Utilities/api.js";

import CarHouseForm from "../Components/CarHouseForm/CarHouseForm.jsx";
import Loading from "../Components/Loading/Loading.jsx";

const CarHouseUpdate = () => {
    const [carHouse, setCarHouse] = useState(null);
    const [loading, setLoading] = useState(true);
    const [carInventoryUpdated, setCarInventoryUpdated] = useState(true);
    const {id} = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        (async () => {
            console.log("UPADTE USE EFFECT");
            const options = {
                method: "GET",
            };
            const userData = JSON.parse(localStorage.getItem("userdata"));
            const headers = {
                "Authorization": `Bearer ${userData.jwt}`,
                "Content-Type": "application/json"
            };
            try {
                //http://localhost:8080/carhouses/id/1
                const carHouseToBeUpdated = await fetchAuthenticated(`/carhouses/id/${id}`, {
                    method: "GET",
                })
                console.log("Car to be Updated " + carHouseToBeUpdated);
                console.log(carHouseToBeUpdated);
                if (carHouseToBeUpdated.ok) {
                    const carHouseToBeUpdatedParsed = await carHouseToBeUpdated.json();
                    console.log(carHouseToBeUpdatedParsed)
                    setCarHouse(carHouseToBeUpdatedParsed);
                    setLoading(false);

                }
            } catch (err) {
                console.log(err);
            }
        })();
    }, [id, carInventoryUpdated]);

    const updateCarHouseHandler = async (carHouse) => {
        console.log("Update Car Handler");
        console.log(carHouse);

        const options = {
            method: "PUT",
            body: JSON.stringify(carHouse)
        };
        try {
            //http://localhost:8080/cars/id/1
            const updatedCarHouse = await fetchAuthenticated(`/carhouses/${id}`, options)
            console.log("updatedCar " + updatedCarHouse);
            console.log(updatedCarHouse);
            if (updatedCarHouse.ok) {
                console.log(updatedCarHouse)
                const updatedCarHouseParsed = await updatedCarHouse.json();
                console.log(updatedCarHouseParsed);
                navigate("/car-house-list")
            }
        } catch (err) {
            console.log(err);
        }

    }
//TODO: HERE
    const deleteCarFromCarHouseHandler = async (carId) => {
        console.log("Delete Car From CarHouse");
        console.log(id);
        const options = {
            method: "POST"
        };
        try {
            const removedCar = await fetchAuthenticated(`/carhouses/${id}/remove-car/${carId}`, options);
            console.log("removedCar");
            console.log(removedCar);
            if (removedCar.ok) {
                const removedCarParsed = await removedCar.json();
                setCarInventoryUpdated(!carInventoryUpdated);
               //navigate(`/car-house-update/${id}`)
                console.log("removedCarParsed");
                console.log(removedCarParsed);
            } else {
                throw new Error("Car hasn`t been deleted");
            }

        } catch (err) {

        }
    }

    if (loading) {
        return <Loading/>;
    }
    return <CarHouseForm
        carHouse={carHouse}
        onSave={updateCarHouseHandler}
        onDelete={deleteCarFromCarHouseHandler}
    />
}
export default CarHouseUpdate;