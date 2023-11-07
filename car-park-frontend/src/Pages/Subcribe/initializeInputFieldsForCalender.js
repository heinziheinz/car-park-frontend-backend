export function initializeInputFieldsForKalender(username, password, birthdate, address) {
    return[
        {
            type: "email",
            className: "first-name",
            name: "name",
            label: "Email",
            placeholder: "Add your name",
            value:username
        },
        {
            type: "password",
            className: "password",
            name: "password",
            label: "password",
            placeholder: "add your password",
            value: password
        },
        {
            type: "date",
            className: "user-name",
            name: "birthdate",
            label: "picker",
            placeholder: "pick a date",
            value: birthdate
        },
        {
            type: "text",
            className: "address",
            name: "address",
            label: "address",
            placeholder: "add your address",
            value: address
        },
    ];

}
