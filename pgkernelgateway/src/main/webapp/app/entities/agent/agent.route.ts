import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Agent } from 'app/shared/model/agent.model';
import { AgentService } from './agent.service';
import { AgentComponent } from './agent.component';
import { AgentDetailComponent } from './agent-detail.component';
import { AgentUpdateComponent } from './agent-update.component';
import { AgentDeletePopupComponent } from './agent-delete-dialog.component';
import { IAgent } from 'app/shared/model/agent.model';

@Injectable({ providedIn: 'root' })
export class AgentResolve implements Resolve<IAgent> {
  constructor(private service: AgentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAgent> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Agent>) => response.ok),
        map((agent: HttpResponse<Agent>) => agent.body)
      );
    }
    return of(new Agent());
  }
}

export const agentRoute: Routes = [
  {
    path: '',
    component: AgentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.agent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AgentDetailComponent,
    resolve: {
      agent: AgentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.agent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AgentUpdateComponent,
    resolve: {
      agent: AgentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.agent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AgentUpdateComponent,
    resolve: {
      agent: AgentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.agent.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const agentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AgentDeletePopupComponent,
    resolve: {
      agent: AgentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.agent.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
