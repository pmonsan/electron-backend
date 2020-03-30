import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgUser } from 'app/shared/model/pg-user.model';

type EntityResponseType = HttpResponse<IPgUser>;
type EntityArrayResponseType = HttpResponse<IPgUser[]>;

@Injectable({ providedIn: 'root' })
export class PgUserService {
  public resourceUrl = SERVER_API_URL + 'api/pg-users';

  constructor(protected http: HttpClient) {}

  create(pgUser: IPgUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pgUser);
    return this.http
      .post<IPgUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pgUser: IPgUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pgUser);
    return this.http
      .put<IPgUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPgUser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPgUser[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pgUser: IPgUser): IPgUser {
    const copy: IPgUser = Object.assign({}, pgUser, {
      creationDate: pgUser.creationDate != null && pgUser.creationDate.isValid() ? pgUser.creationDate.toJSON() : null,
      updateDate: pgUser.updateDate != null && pgUser.updateDate.isValid() ? pgUser.updateDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate != null ? moment(res.body.creationDate) : null;
      res.body.updateDate = res.body.updateDate != null ? moment(res.body.updateDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pgUser: IPgUser) => {
        pgUser.creationDate = pgUser.creationDate != null ? moment(pgUser.creationDate) : null;
        pgUser.updateDate = pgUser.updateDate != null ? moment(pgUser.updateDate) : null;
      });
    }
    return res;
  }
}
