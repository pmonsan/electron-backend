/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgAccountComponent } from 'app/entities/pg-account/pg-account.component';
import { PgAccountService } from 'app/entities/pg-account/pg-account.service';
import { PgAccount } from 'app/shared/model/pg-account.model';

describe('Component Tests', () => {
  describe('PgAccount Management Component', () => {
    let comp: PgAccountComponent;
    let fixture: ComponentFixture<PgAccountComponent>;
    let service: PgAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgAccountComponent],
        providers: []
      })
        .overrideTemplate(PgAccountComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgAccountComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgAccountService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgAccount(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgAccounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
