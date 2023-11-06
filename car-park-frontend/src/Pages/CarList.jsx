import {useEffect, useState} from "react";
import {jwtTokenFetch} from "../Utilities/jwtTokenFetch.js";
import CarTableCompleteList from "../Components/CarTableCompleteList.jsx";
import Login from "./Login.jsx";

const CarList = () => {
    const [loading, setLoading] = useState(true);
    const [cars, setCars] = useState([]);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);

    const handleDelete = (id) => {
        console.log("delete Car with id:" + id);
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
                    console.log(allCarsParsed?.totalPages);
                    setTotalPages(allCarsParsed?.totalPages);
                    console.log(allCarsParsed);
                    setCars(allCarsParsed.content)
                }
            } catch (err) {
                console.log(err);
            }
        })();
    }, [currentPage]);

    const flipThePage = (event) => {
        console.log(event.target.name)
        // / api / employee - pagination ? page = 2
        let myCurrentPage;
        if (event.target.name === "plus") {
            console.log("plus")
            myCurrentPage = currentPage + 1;
        }
        if (event.target.name === "minus") {
            console.log("minus")
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