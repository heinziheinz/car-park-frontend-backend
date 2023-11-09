const CarsForCarHouse = ({currentPage, totalPages, setCurrentPage, cars, deleteHandler}) => {

    const flipThePage = (event) => {
        let myCurrentPage;
        if (event.target.name === "plus") {
            myCurrentPage = currentPage + 1;
        }
        if (event.target.name === "minus") {
            myCurrentPage = currentPage - 1;
        }
        setCurrentPage(myCurrentPage);
    }
    console.log(cars)

    return (
        <>
            <div className="EmployeeTable">
                <table>
                    <thead>
                    <tr>
                        <th name="name">name</th>
                        <th name="type">price</th>
                    </tr>
                    </thead>
                    <tbody>
                    {cars.map((car) => (
                        <tr key={car.id}>
                            <td>{car.typeName}</td>
                            <td>{car.price}</td>
                            <td><img src={car.image} width="200" height="100"/></td>
                            <td>
                                <button type="button" onClick={() => deleteHandler(car.id)}>
                                    {"Remove Car From Car House"}
                                </button>
                            </td>

                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
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
export default CarsForCarHouse;