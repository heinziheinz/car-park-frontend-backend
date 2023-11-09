import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {fetchAuthenticated} from "../../Utilities/api.js";
import {fetchAllCarsInCarHouse, fetchAllCarsNotAllocatedToCArHouse, fetchCarHouse} from "./UseCarHouseUpadteApi.js";

const useCarHouseUpdate = () => {
    const [carHouse, setCarHouse] = useState(null);
    const [carsNotInCarHouse, setCarsNotInCarHouse] = useState([]);
    const [carsInCarHouse, setCarsInCarHouse] = useState([]);


    const [loading, setLoading] = useState(true);
    const [carInventoryUpdated, setCarInventoryUpdated] = useState(true);
    const {id} = useParams();

    const [totalPagesNotAllocated, setTotalPagesNotAllocated] = useState(0);
    const [currentPageNotAllocated, setCurrentPageNotAllocated] = useState(0);
    const [totalPagesCarsInCarHouse, setTotalPagesCarsInCarHouse] = useState(0);
    const [currentPageInCarHouse, setCurrentPageInCarHouse] = useState(0);

    const navigate = useNavigate();

    useEffect(() => {
        (async () => {

            try {
                const carHouse = await fetchCarHouse(id);
                setCarHouse(carHouse);

                const allCarsNotAllocated = await fetchAllCarsNotAllocatedToCArHouse(currentPageNotAllocated);
                setCarsNotInCarHouse(allCarsNotAllocated.content)
                setTotalPagesNotAllocated(allCarsNotAllocated?.totalPages);

                const allCarsInCArHouse = await fetchAllCarsInCarHouse(id,currentPageInCarHouse);
                setCarsInCarHouse(allCarsInCArHouse.content);
                setTotalPagesCarsInCarHouse(allCarsInCArHouse.totalPages);

                setLoading(false);
            } catch (err) {
                console.log(err);
            }
        })();
    }, [id, currentPageNotAllocated, currentPageInCarHouse, carInventoryUpdated]);

    const updateCarHouseHandler = async (carHouse) => {
        const options = {
            method: "PUT",
            body: JSON.stringify(carHouse)
        };
        try {
            const updatedCarHouse = await fetchAuthenticated(`/carhouses/${id}`, options)
            if (updatedCarHouse.ok) {
                const updatedCarHouseParsed = await updatedCarHouse.json();
                navigate("/car-house-list")
            }
        } catch (err) {
            console.log(err);
        }

    }


    const removeCarFromCarHouseHandler = async (carId) => {
        const options = {
            method: "POST"
        };
        try {
            const removedCar = await fetchAuthenticated(`/carhouses/${id}/remove-car/${carId}`, options);
            if (removedCar.ok) {
                const removedCarParsed = await removedCar.json();
                setCarInventoryUpdated(!carInventoryUpdated);

            } else {
                throw new Error("Car hasn`t been deleted");
            }

        } catch (err) {

        }

    }
    const adCarToCarHouseHandler = async (carId) => {
        try {
            const carAddedToCarHouse = await fetchAuthenticated(`/carhouses/${id}/cars/${carId}`, {
                method: "POST"
            });
            if (carAddedToCarHouse.ok) {
                const carAddedToCarHouseParsed = await carAddedToCarHouse.json();
                setLoading(false);
                setCarInventoryUpdated(!carInventoryUpdated);

            } else {
                throw new Error("Error with Fetch call allCarsInCarHouse");
            }
        } catch (err) {
            console.error(err);
        }
    }

    const setCurrentPageInCarHouseHandler = (page) => {
        setCurrentPageInCarHouse(page);
    }

    const setCurrentPageNotAllocatedHandler = (page) => {
        setCurrentPageNotAllocated(page);
    }
    return {
        setCurrentPageNotAllocatedHandler,
        setCurrentPageInCarHouseHandler,
        adCarToCarHouseHandler,
        removeCarFromCarHouseHandler,
        updateCarHouseHandler,
        carHouse,
        carsNotInCarHouse,
        carsInCarHouse,
        loading,
        totalPagesNotAllocated,
        totalPagesCarsInCarHouse,
        currentPageInCarHouse,
        currentPageNotAllocated
    };
}

export default useCarHouseUpdate;