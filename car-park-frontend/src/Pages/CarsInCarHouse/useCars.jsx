import {useParams, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {fetchAuthenticated} from "../../Utilities/api.js";

const useCars = () => {
    const {id} = useParams();
    const [carsInCarHouse, setCarsInCarHouse] = useState([]);
    const [loading, setLoading] = useState(true);
    const [carInventoryUpdated, setCarInventoryUpdated] = useState(true);
    const [totalPagesCarsInCarHouse, setTotalPagesCarsInCarHouse] = useState(0);
    const [currentPageInCarHouse, setCurrentPageInCarHouse] = useState(0);
    const navigate = useNavigate()

    useEffect(() => {
        console.log("id")
        console.log(id);
        (async () => {

            try {
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
                console.error(err);
            }
        })();
    }, [carInventoryUpdated, currentPageInCarHouse])

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
            myCurrentPage = currentPageInCarHouse + 1;
        }
        if (event.target.name === "minus") {
            myCurrentPage = currentPageInCarHouse - 1;
        }
        setCurrentPageInCarHouse(myCurrentPage);
    }
    return {deleteHandler, loading, cars: carsInCarHouse, totalPages: totalPagesCarsInCarHouse, currentPage: currentPageInCarHouse, flipThePage};

}
export default useCars;