/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgUserComponent } from 'app/entities/pg-user/pg-user.component';
import { PgUserService } from 'app/entities/pg-user/pg-user.service';
import { PgUser } from 'app/shared/model/pg-user.model';

describe('Component Tests', () => {
  describe('PgUser Management Component', () => {
    let comp: PgUserComponent;
    let fixture: ComponentFixture<PgUserComponent>;
    let service: PgUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgUserComponent],
        providers: []
      })
        .overrideTemplate(PgUserComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgUserComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgUserService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgUser(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
