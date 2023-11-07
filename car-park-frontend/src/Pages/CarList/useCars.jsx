import {useEffect, useState} from "react";
import {jwtTokenFetch} from "../../Utilities/jwtTokenFetch.js";
import {fetchAuthenticated} from "../../Utilities/api.js";


const useAllCars = () => {
    const [loading, setLoading] = useState(true);
    const [cars, setCars] = useState([]);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);
    const [carDeleted, setCarDeleted] = useState(false);
    useEffect(() => {

        (async () => {

            try {
                const allCars = await fetchAuthenticated(`/cars?page=${currentPage}&size=10`, {
                    method: "GET"
                });
                if (allCars.ok) {
                    const allCarsParsed = await allCars.json();
                    setLoading(false);
                    setTotalPages(allCarsParsed?.totalPages);
                    setCars(allCarsParsed.content)
                } else {
                    throw new Error("All carts error");
                }
            } catch (err) {
                console.error(err);
            }
        })();
    }, [currentPage, carDeleted]);
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
    return {handleDelete, loading, cars, totalPages, currentPage, flipThePage};
}

export default useAllCars;