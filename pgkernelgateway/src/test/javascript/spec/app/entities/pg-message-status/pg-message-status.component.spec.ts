/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgMessageStatusComponent } from 'app/entities/pg-message-status/pg-message-status.component';
import { PgMessageStatusService } from 'app/entities/pg-message-status/pg-message-status.service';
import { PgMessageStatus } from 'app/shared/model/pg-message-status.model';

describe('Component Tests', () => {
  describe('PgMessageStatus Management Component', () => {
    let comp: PgMessageStatusComponent;
    let fixture: ComponentFixture<PgMessageStatusComponent>;
    let service: PgMessageStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgMessageStatusComponent],
        providers: []
      })
        .overrideTemplate(PgMessageStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgMessageStatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgMessageStatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgMessageStatus(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgMessageStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
