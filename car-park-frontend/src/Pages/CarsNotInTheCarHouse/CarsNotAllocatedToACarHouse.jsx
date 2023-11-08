import {useEffect, useState} from "react";
import {fetchAuthenticated} from "../../Utilities/api.js";
import {useParams, useNavigate} from "react-router-dom";
import Loading from "../../Components/Loading/Loading.jsx";
import CarsFromCarHouseTable from "./../../Components/CarsFromCarHouseTable/CarsFromCarHouseTable.jsx";

const CarsNotAllocatedToACarHouse = () => {
    const {id} = useParams();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);
    const [cars, setCars] = useState([]);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);
    useEffect(() => {
        (async () => {

            const options = {
                method: "GET"
            };
            //http://localhost:8080/cars/all-cars-not-allocated-to-a-carhouse?page=${currentPage}&size=10
            try {
                const allCarsNotAllocatedToACarHouse = await fetchAuthenticated(`/cars/all-cars-not-allocated-to-a-carhouse?page=${currentPage}&size=10`, options);
                if (allCarsNotAllocatedToACarHouse.ok) {
                    const allCarsNotAllocatedToACarHouseParsed = await allCarsNotAllocatedToACarHouse.json();
                    console.log(allCarsNotAllocatedToACarHouseParsed.content)
                    setCars(allCarsNotAllocatedToACarHouseParsed.content)
                    setTotalPages(allCarsNotAllocatedToACarHouseParsed?.totalPages);
                    setLoading(false);

                } else {
                    throw new Error("Error with Fetch call allCarsInCarHouse");
                }
            } catch (err) {
                console.error(err);
            }
        })();
    }, [currentPage]);

    const adCarToCarHouse = async (carId) => {

        const options = {
            method: "POST"
        };
        //http://localhost:8080/carhouses/1/cars/1
        try {
            const carAddedToCarHouse = await fetchAuthenticated(`/carhouses/${id}/cars/${carId}`, options);
            if (carAddedToCarHouse.ok) {
                const carAddedToCarHouseParsed = await carAddedToCarHouse.json();
                console.log(carAddedToCarHouseParsed.content)
                setLoading(false);
                //navigate("/car-house-list")
                navigate(`/redirect/${id}`)

            } else {
                throw new Error("Error with Fetch call allCarsInCarHouse");
            }
        } catch (err) {
            console.error(err);
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
    console.log("cars")
    console.log(cars)
    return (
        <>
            <CarsFromCarHouseTable
                cars={cars}
                onDelete={adCarToCarHouse}
                value={"Add Car To CarHouse"}
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
export default CarsNotAllocatedToACarHouse;