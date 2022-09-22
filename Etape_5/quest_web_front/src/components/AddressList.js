import { useEffect, useState } from "react";
import AddressListItem from "./AddressListItem";
import AddressService from "../services/address.service";


const AddressList = () => {

    const [addressList, setAddressList] = useState( [] );

    useEffect(() => {
        if (!addressList.length) {
            AddressService.getAll().then((response) => {
                if (response.status === 200) {
                    console.log(response);
                    setAddressList(
                        response.data.sort((a, b) => a.user.user_id - b.user.user_id)
                    );
                } else if (response.status === 401) {

                }
            });
        }
    });

    return(
    <ul>
        {addressList.map(address => (
            <AddressListItem key={address.id} address={address} />
        ))}
    </ul>
)};

export default AddressList;