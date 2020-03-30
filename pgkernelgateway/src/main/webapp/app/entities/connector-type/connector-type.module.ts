import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  ConnectorTypeComponent,
  ConnectorTypeDetailComponent,
  ConnectorTypeUpdateComponent,
  ConnectorTypeDeletePopupComponent,
  ConnectorTypeDeleteDialogComponent,
  connectorTypeRoute,
  connectorTypePopupRoute
} from './';

const ENTITY_STATES = [...connectorTypeRoute, ...connectorTypePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ConnectorTypeComponent,
    ConnectorTypeDetailComponent,
    ConnectorTypeUpdateComponent,
    ConnectorTypeDeleteDialogComponent,
    ConnectorTypeDeletePopupComponent
  ],
  entryComponents: [
    ConnectorTypeComponent,
    ConnectorTypeUpdateComponent,
    ConnectorTypeDeleteDialogComponent,
    ConnectorTypeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayConnectorTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
