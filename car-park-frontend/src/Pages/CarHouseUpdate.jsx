import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {fetchAuthenticated} from "../Utilities/api.js";
import CarHouseForm from "../Components/CarHouseForm/CarHouseForm.jsx";
import Loading from "../Components/Loading/Loading.jsx";
import CarsInCarHouse from "./CarsInCarHouse/CarsInCarHouse.jsx";
import CarsNotAllocatedToACarHouse from "./CarsNotInTheCarHouse/CarsNotAllocatedToACarHouse.jsx";
import CarsForCarHouse from "./CarsForCarHouse/CarsForCarHouse.jsx";

const CarHouseUpdate = () => {
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
                    console.log(carHouseToBeUpdatedParsed)
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
                    console.log(allCarsNotAllocatedToACarHouseParsed.content)
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
            console.log("updatedCar " + updatedCarHouse);
            console.log(updatedCarHouse);
            if (updatedCarHouse.ok) {
                console.log(updatedCarHouse)
                const updatedCarHouseParsed = await updatedCarHouse.json();
                console.log(updatedCarHouseParsed);
                navigate("/car-house-list")
                //navigate(`/redirect/${id}`)
            }
        } catch (err) {
            console.log(err);
        }

    }

    const deleteHandler = async (carId) => {
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
                //navigate(`/redirect/${id}`)


            } else {
                throw new Error("Car hasn`t been deleted");
            }

        } catch (err) {

        }

    }

    const setCurrentPageInCarHouseHandler = (page) => {
        setCurrentPageInCarHouse(page);
    }

    if (loading) {
        return <Loading/>;
    }
    return <CarHouseForm
        carHouse={carHouse}
        onSave={updateCarHouseHandler}
    >
        {
            <CarsForCarHouse
                cars={carsInCarHouse}
                totalPages={totalPagesCarsInCarHouse}
                currentPage={currentPageInCarHouse}
                setCurrentPage={setCurrentPageInCarHouseHandler}
                deleteHandler={deleteHandler}
            />
        }
        {<CarsNotAllocatedToACarHouse/>}
    </CarHouseForm>
    /*return <CarHouseForm
         carHouse={carHouse}
         onSave={updateCarHouseHandler}
     >{<CarsInCarHouse/>}{<CarsNotAllocatedToACarHouse/>}</CarHouseForm>*/
}
export default CarHouseUpdate;