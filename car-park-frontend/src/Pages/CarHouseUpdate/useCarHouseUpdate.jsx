import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {fetchAuthenticated} from "../../Utilities/api.js";

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
                const carHouseToBeUpdated = await fetchAuthenticated(`/carhouses/id/${id}`, {
                    method: "GET",
                })
                if (carHouseToBeUpdated.ok) {
                    const carHouseToBeUpdatedParsed = await carHouseToBeUpdated.json();
                    setCarHouse(carHouseToBeUpdatedParsed);
                    setLoading(false);
                } else {
                    throw new Error("Error with Fetch call allCarsInCarHouse");
                }
                const allCarsNotAllocatedToACarHouse = await fetchAuthenticated(`/cars/all-cars-not-allocated-to-a-carhouse?page=${currentPageNotAllocated}&size=10`, {
                    method: "GET"
                });
                if (allCarsNotAllocatedToACarHouse.ok) {
                    const allCarsNotAllocatedToACarHouseParsed = await allCarsNotAllocatedToACarHouse.json();
                    setCarsNotInCarHouse(allCarsNotAllocatedToACarHouseParsed.content)
                    setTotalPagesNotAllocated(allCarsNotAllocatedToACarHouseParsed?.totalPages);

                } else {
                    throw new Error("Error with Fetch call allCarsInCarHouse");
                }
                const allCarsInCarHouse = await fetchAuthenticated(`/cars/find-all-cars-with-in-carhouse/${id}?page=${currentPageInCarHouse}&size=10`, {
                    method: "GET"
                });
                if (allCarsInCarHouse.ok) {
                    const allCarsFormCarHouseParsed = await allCarsInCarHouse.json();
                    setCarsInCarHouse(allCarsFormCarHouseParsed.content);
                    setTotalPagesCarsInCarHouse(allCarsFormCarHouseParsed?.totalPages);
                    setLoading(false);

                } else {
                    throw new Error("Error with Fetch call allCarsInCarHouse");
                }
            } catch (err) {
                console.log(err);
            }
        })();
    }, [id, currentPageNotAllocated, currentPageInCarHouse, carInventoryUpdated]);

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
            if (updatedCarHouse.ok) {
                const updatedCarHouseParsed = await updatedCarHouse.json();
                navigate("/car-house-list")
            }
        } catch (err) {
            console.log(err);
        }

    }


    const removeCarFromCarHouseHandler = async (carId) => {
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