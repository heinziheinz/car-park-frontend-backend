import {useEffect, useState} from "react";
import {fetchAuthenticated} from "./../../Utilities/api.js";
import {useParams} from "react-router-dom";
import Loading from "../../Components/Loading/Loading.jsx";
import CarsFromCarHouseTable from "../../Components/CarsFromCarHouseTable/CarsFromCarHouseTable.jsx";

const CarsInCarHouse = () => {
    const {id} = useParams();
    const [cars, setCars] = useState([]);
    const [loading, setLoading] = useState(true);
    const [carInventoryUpdated, setCarInventoryUpdated] = useState(true);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);

    useEffect(() => {
        console.log("id")
        console.log(id);
        (async () => {
            const options = {
                method: "GET"
            };
            try {
                const allCarsInCarHouse = await fetchAuthenticated(`/cars/find-all-cars-with-in-carhouse/${id}`, options);
                if (allCarsInCarHouse.ok) {
                    const allCarsFormCarHouseParsed = await allCarsInCarHouse.json();
                    setCars(allCarsFormCarHouseParsed.content);
                    setTotalPages(allCarsFormCarHouseParsed?.totalPages);
                    setLoading(false);

                } else {
                    throw new Error("Error with Fetch call allCarsInCarHouse");
                }
            } catch (err) {
                console.error(err);
            }
        })();
    }, [carInventoryUpdated])

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
            } else {
                throw new Error("Car hasn`t been deleted");
            }

        } catch (err) {

        }

    }

    const flipThePage = (event) => {
        console.log(event.target.name)
        let myCurrentPage;
        if (event.target.name === "plus") {
            myCurrentPage = currentPage + 1;
        }
        if (event.target.name === "minus") {
            myCurrentPage = currentPage - 1;
        }
        setCurrentPage(myCurrentPage);
    }
    if (loading) {
        return <Loading/>
    }
    return (
        <>
            <CarsFromCarHouseTable
                cars={cars}
                onDelete={deleteHandler}
            />
            <button
                onClick={flipThePage}
                name="minus"
                disabled={currentPage === 0}>Previous
            </button>
            <button
                onClick={flipThePage}
                name="plus" disabled={currentPage === totalPages - 1}>Next
            </button>
        </>
    );
}
export default CarsInCarHouse;