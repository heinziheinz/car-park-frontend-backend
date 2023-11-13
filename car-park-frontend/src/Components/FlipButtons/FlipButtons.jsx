const FlipButtons = ({currentPage, totalPages, flipThePage}) => {
    console.log("totalPages");
    console.log(totalPages);
    console.log("currentPage");
    console.log(currentPage);
    return (
        <>
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
export default FlipButtons;