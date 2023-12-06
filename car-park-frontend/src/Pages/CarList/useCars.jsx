import {useEffect, useState} from "react";
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

        try {
            const deletedCar = await fetchAuthenticated(`/cars/${id}`, {
                method: "DELETE"
            });
            if (deletedCar.ok) {
                const deletedCarParsed = await deletedCar.json();
                setCarDeleted(!carDeleted);
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