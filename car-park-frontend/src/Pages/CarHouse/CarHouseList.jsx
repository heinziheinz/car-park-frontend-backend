import {useEffect, useState} from "react";
import {fetchAuthenticated} from "../../Utilities/api.js";
import Loading from "../../Components/Loading/Loading.jsx";
import CarHouseTable from "../../Components/CarHouseTable/CarHouseTable.jsx";
import {jwtTokenFetch} from "../../Utilities/jwtTokenFetch.js";
import useAllCars from "../CarList/useCars.jsx";

import userCarHouse from "./useCarHouse.jsx"
const CarHouseList = () => {
    const {carHouses, handleDelete, loading, totalPages, currentPage, flipThePage} = userCarHouse();
    if (loading) {
        return <Loading/>;
    }

    return (
        <>
            <CarHouseTable
                carHouses={carHouses}
                onDelete={handleDelete}
            />
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

export default CarHouseList;