import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserConnection } from 'app/shared/model/user-connection.model';

type EntityResponseType = HttpResponse<IUserConnection>;
type EntityArrayResponseType = HttpResponse<IUserConnection[]>;

@Injectable({ providedIn: 'root' })
export class UserConnectionService {
  public resourceUrl = SERVER_API_URL + 'api/user-connections';

  constructor(protected http: HttpClient) {}

  create(userConnection: IUserConnection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userConnection);
    return this.http
      .post<IUserConnection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userConnection: IUserConnection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userConnection);
    return this.http
      .put<IUserConnection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserConnection>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserConnection[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userConnection: IUserConnection): IUserConnection {
    const copy: IUserConnection = Object.assign({}, userConnection, {
      loginDate: userConnection.loginDate != null && userConnection.loginDate.isValid() ? userConnection.loginDate.toJSON() : null,
      logoutDate: userConnection.logoutDate != null && userConnection.logoutDate.isValid() ? userConnection.logoutDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.loginDate = res.body.loginDate != null ? moment(res.body.loginDate) : null;
      res.body.logoutDate = res.body.logoutDate != null ? moment(res.body.logoutDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userConnection: IUserConnection) => {
        userConnection.loginDate = userConnection.loginDate != null ? moment(userConnection.loginDate) : null;
        userConnection.logoutDate = userConnection.logoutDate != null ? moment(userConnection.logoutDate) : null;
      });
    }
    return res;
  }
}
