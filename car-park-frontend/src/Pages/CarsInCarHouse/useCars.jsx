import {useParams, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {fetchAuthenticated} from "../../Utilities/api.js";

const useCars = () => {
    const {id} = useParams();
    const [cars, setCars] = useState([]);
    const [loading, setLoading] = useState(true);
    const [carInventoryUpdated, setCarInventoryUpdated] = useState(true);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);
    const navigate = useNavigate()

    useEffect(() => {
        console.log("id")
        console.log(id);
        (async () => {
            const options = {
                method: "GET"
            };
            //http://localhost:8080/cars/find-all-cars-with-in-carhouse/1?page=1&size=10
            try {
                const allCarsInCarHouse = await fetchAuthenticated(`/cars/find-all-cars-with-in-carhouse/${id}?page=${currentPage}&size=10`, options);
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
    }, [carInventoryUpdated, currentPage])

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
                navigate(`/redirect/${id}`)


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
    return {deleteHandler, loading, cars, totalPages, currentPage, flipThePage};

}
export default useCars;