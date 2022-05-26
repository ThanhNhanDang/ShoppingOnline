import { Observable } from 'rxjs';
export declare class State {
    private _authState;
    get authState(): Observable<AuthenticatorResponse>;

}
