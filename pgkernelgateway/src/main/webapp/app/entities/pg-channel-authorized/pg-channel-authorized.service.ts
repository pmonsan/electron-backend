import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgChannelAuthorized } from 'app/shared/model/pg-channel-authorized.model';

type EntityResponseType = HttpResponse<IPgChannelAuthorized>;
type EntityArrayResponseType = HttpResponse<IPgChannelAuthorized[]>;

@Injectable({ providedIn: 'root' })
export class PgChannelAuthorizedService {
  public resourceUrl = SERVER_API_URL + 'api/pg-channel-authorizeds';

  constructor(protected http: HttpClient) {}

  create(pgChannelAuthorized: IPgChannelAuthorized): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pgChannelAuthorized);
    return this.http
      .post<IPgChannelAuthorized>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pgChannelAuthorized: IPgChannelAuthorized): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pgChannelAuthorized);
    return this.http
      .put<IPgChannelAuthorized>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPgChannelAuthorized>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPgChannelAuthorized[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pgChannelAuthorized: IPgChannelAuthorized): IPgChannelAuthorized {
    const copy: IPgChannelAuthorized = Object.assign({}, pgChannelAuthorized, {
      registrationDate:
        pgChannelAuthorized.registrationDate != null && pgChannelAuthorized.registrationDate.isValid()
          ? pgChannelAuthorized.registrationDate.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.registrationDate = res.body.registrationDate != null ? moment(res.body.registrationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pgChannelAuthorized: IPgChannelAuthorized) => {
        pgChannelAuthorized.registrationDate =
          pgChannelAuthorized.registrationDate != null ? moment(pgChannelAuthorized.registrationDate) : null;
      });
    }
    return res;
  }
}
