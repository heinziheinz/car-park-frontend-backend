import {useEffect, useState} from "react";
import {jwtTokenFetch} from "../Utilities/jwtTokenFetch.js";
import CarTableCompleteList from "../Components/CarTableCompleteList.jsx";
import Login from "./Login.jsx";

const CarList = () => {
    const [loading, setLoading] = useState(true);
    const [cars, setCars] = useState([]);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);
    const [carDeleted, setCarDeleted] = useState(false);

    const handleDelete = async (id) => {
        console.log("delete Car with id:" + id);
        const options = {
            method: "DELETE",
        };
        const userData = JSON.parse(localStorage.getItem("userdata"));
        const headers = {
            "Authorization": `Bearer ${userData.jwt}`,
            "Content-Type": "application/json"
        };
        try {
            const deletedCar = await jwtTokenFetch(`/cars/${id}`, options, headers)
            console.log("deletedCar " + deletedCar);
            console.log(deletedCar);
            if (deletedCar.ok) {
                console.log("Inside");
                console.log(deletedCar);
                const deletedCarParsed = await deletedCar.json();
                console.log("After");
                setCarDeleted(!carDeleted);

                console.log(deletedCarParsed);
            }
        } catch (err) {
            console.log(err);
        }

    };

    useEffect(() => {
        (async () => {
            const options = {
                method: "GET",
            };
            const userData = JSON.parse(localStorage.getItem("userdata"));
            const headers = {
                "Authorization": `Bearer ${userData.jwt}`,
                "Content-Type": "application/json"
            };
            try {
                const allCars = await jwtTokenFetch(`/cars?page=${currentPage}&size=10`, options, headers)
                console.log("All Cars " + allCars);
                console.log(allCars);
                if (allCars.ok) {
                    const allCarsParsed = await allCars.json();
                    setLoading(false);
                    setTotalPages(allCarsParsed?.totalPages);
                    setCars(allCarsParsed.content)
                }
            } catch (err) {
                console.log(err);
            }
        })();
    }, [currentPage, carDeleted]);

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
        return <Login/>;
    }
    return (
        <>
            <CarTableCompleteList cars={cars} onDelete={handleDelete}/>
            <div>
                <button
                    onClick={flipThePage}
                    name="minus"
                    disabled={currentPage === 0}>Previous
                </button>
                <button
                    onClick={flipThePage}
                    name="plus" disabled={currentPage === totalPages - 1}>Next
                </button>
            </div>
        </>
    );
}
export default CarList;