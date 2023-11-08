import {useEffect, useState} from "react";
import {fetchAuthenticated} from "./../../Utilities/api.js";
import {useParams} from "react-router-dom";
import Loading from "../../Components/Loading/Loading.jsx";
import CarsFromCarHouseTable from "../../Components/CarsFromCarHouseTable/CarsFromCarHouseTable.jsx";
import useCars from "./useCars.jsx";

const CarsInCarHouse = () => {
   const{loading,cars, deleteHandler, flipThePage, currentPage, totalPages}= useCars();
    if (loading) {
        return <Loading/>
    }
    return (
        <>
            <CarsFromCarHouseTable
                cars={cars}
                onDelete={deleteHandler}
                value={"Delete Car From CarHouse"}
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