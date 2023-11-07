import {useEffect, useState} from "react";
import {fetchAuthenticated} from "./../Utilities/api.js";
import Loading from "../Components/Loading/Loading.jsx";
import CarHouseTable from "../Components/CarHouseTable/CarHouseTable.jsx";
import {jwtTokenFetch} from "../Utilities/jwtTokenFetch.js";

const CarHouseList = () => {
    const [loading, setLoading] = useState(true);
    const [carHouses, setCarHouses] = useState([]);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);
    const [carHouseDeleted, setCarHouseDeleted] = useState(false);

    useEffect(() => {

        (async () => {
            try {
                const allCarHouses = await fetchAuthenticated(`/carhouses?page=0&size=10`, {
                    method: "GET"
                });
                if (allCarHouses.ok) {
                    const allCarHousesParsed = await allCarHouses.json();
                    setLoading(false);
                    setTotalPages(allCarHousesParsed?.totalPages);
                    setCarHouses(allCarHousesParsed.content)
                } else {
                    throw new Error("All carts error");
                }
            } catch (err) {
                console.error(err);
            }
        })();
    }, [carHouseDeleted]);

    const handleDelete = async (id) => {
        console.log(id);
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
            const deletedCarHouse = await fetchAuthenticated(`/carhouses/${id}`, {
                method: "DELETE"
            });
            console.log("deletedCarHouse " + deletedCarHouse);
            if (deletedCarHouse.ok) {
                const deletedCarHouseParsed = await deletedCarHouse.json();
                console.log("After");
                setCarHouseDeleted(!carHouseDeleted);

                console.log(deletedCarHouseParsed);
            }
        } catch (err) {
            console.log(err);
        }

    };

    if (loading) {
        return <Loading/>;
    }

    return <CarHouseTable
        carHouses={carHouses}
        onDelete={handleDelete}
    />;
}

export default CarHouseList;