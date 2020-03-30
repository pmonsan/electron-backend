import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PeriodicityComponent,
  PeriodicityDetailComponent,
  PeriodicityUpdateComponent,
  PeriodicityDeletePopupComponent,
  PeriodicityDeleteDialogComponent,
  periodicityRoute,
  periodicityPopupRoute
} from './';

const ENTITY_STATES = [...periodicityRoute, ...periodicityPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PeriodicityComponent,
    PeriodicityDetailComponent,
    PeriodicityUpdateComponent,
    PeriodicityDeleteDialogComponent,
    PeriodicityDeletePopupComponent
  ],
  entryComponents: [PeriodicityComponent, PeriodicityUpdateComponent, PeriodicityDeleteDialogComponent, PeriodicityDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPeriodicityModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
